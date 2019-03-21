package io.sunshower.sdk.v1.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.core.security.UserService;
import io.sunshower.core.security.crypto.EncryptionService;
import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.model.core.events.CacheEvictionEvent;
import io.sunshower.sdk.lang.BooleanElement;
import io.sunshower.sdk.v1.endpoints.core.security.UserEndpoint;
import io.sunshower.sdk.v1.model.core.Registrations;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.sdk.v1.model.ext.LocaleElement;
import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public class DefaultUserEndpoint implements UserEndpoint {

  @Inject private Users users;
  @Inject private UserService userService;
  @Inject private Registrations registrations;
  @Inject private EncryptionService encryptionService;
  @Inject private ApplicationContext applicationContext;
  @PersistenceContext private EntityManager entityManager;

  @Override
  public List<LocaleElement> getLocales() {
    return getLocales(Locale.getDefault());
  }

  @Override
  public List<LocaleElement> getLocales(Locale locale) {
    return Arrays.stream(Locale.getAvailableLocales())
        .map(this::mapLocale)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("hasPermission(#userId, 'io.sunshower.model.core.auth.User', 'READ')")
  public PrincipalElement get(Identifier userId) {
    val user = userService.get(userId);
    val userEl = users.completeElement(user);
    userEl.setImage(registrations.imageElement(user.getDetails().getImage()));
    return userEl;
  }

  @Override
  @Transactional
  @PreAuthorize("hasPermission(#userId, 'io.sunshower.model.core.auth.User', 'WRITE')")
  public void update(Identifier userId, PrincipalElement element) {
    val user = entityManager.find(User.class, userId);
    if (user == null) {
      throw new EntityNotFoundException("No user with that ID");
    }
    val details = user.getDetails();

    if (dirty(details.getFirstName(), element.getFirstName())) {
      details.setFirstName(element.getFirstName());
    }

    if (dirty(details.getLastName(), element.getLastName())) {
      details.setLastName(element.getLastName());
    }

    if (dirty(details.getLocale(), element.getLocale())) {
      details.setLocale(element.getLocale());
    }

    if (dirty(details.getPhoneNumber(), element.getPhoneNumber())) {
      details.setPhoneNumber(element.getPhoneNumber());
    }

    if (element.getPassword() != null) {
      user.setPassword(encryptionService.encrypt(element.getPassword()));
    }

    applicationContext.publishEvent(new CacheEvictionEvent(userId, User.class, this));
    entityManager.flush();
  }

  private boolean dirty(Object source, Object target) {
    return target != null && !Objects.equals(source, target);
  }

  @Override
  @Transactional
  @PreAuthorize("hasAuthority('admin')")
  public BooleanElement delete(Identifier userid) {
    User user = entityManager.find(User.class, userid);
    if (user == null) {
      return BooleanElement.False;
    }
    final List<Role> roles = new ArrayList<>(user.getRoles());
    for (Role role : roles) {
      user.removeRole(role);
    }
    entityManager.remove(user);
    return BooleanElement.True;
  }

  @Override
  public List<PrincipalElement> list(boolean active) {
    List<User> results = active ? userService.activeUsers() : userService.inactiveUsers();
    return results.stream().map(users::toElement).collect(Collectors.toList());
  }

  private LocaleElement mapLocale(Locale locale) {
    final LocaleElement el = new LocaleElement();
    el.setDisplayName(locale.getDisplayName());
    el.setLocaleKey(locale.toLanguageTag());
    return el;
  }
}
