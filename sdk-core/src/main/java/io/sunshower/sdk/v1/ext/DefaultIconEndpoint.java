package io.sunshower.sdk.v1.ext;

import io.sunshower.common.Identifier;
import io.sunshower.core.security.UserService;
import io.sunshower.lang.tuple.Pair;
import io.sunshower.model.core.ImageAware;
import io.sunshower.model.core.events.CacheEvictionEvent;
import io.sunshower.sdk.v1.endpoints.ext.IconEndpoint;
import io.sunshower.sdk.v1.model.core.Registrations;
import io.sunshower.sdk.v1.model.ext.ChangeIconRequest;
import io.sunshower.sdk.v1.model.ext.ImageElement;
import io.sunshower.service.ext.IconService;
import io.sunshower.service.security.PermissionsService;
import io.sunshower.service.security.Session;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.transaction.annotation.Transactional;

@Path("icon")
@Transactional
public class DefaultIconEndpoint implements IconEndpoint {

  @Inject private ApplicationEventPublisher publisher;

  @Inject private Session session;
  @Inject private UserService userService;
  @Inject private IconService iconService;
  @Inject private Registrations registrations;
  @PersistenceContext private EntityManager entityManager;
  @Inject private PermissionsService<Permission> permissionsService;

  @Override
  @PreAuthorize("isFullyAuthenticated()")
  public void reset() {
    val user = userService.findByUsername(session.getUsername());
    if (user == null) {
      throw new EntityNotFoundException("No user");
    }
    permissionsService.checkPermission(user, BasePermission.WRITE);
    val icon = iconService.iconDirect(session.getUsername(), 64, 64);
    user.getDetails().setImage(icon);
    entityManager.flush();
  }

  @Override
  public void setIcon(Identifier id, ChangeIconRequest request) {
    Pair<ImageAware, Class<? extends ImageAware>> loaded = load(id, request.getTargetType());
    val image = registrations.toImage(request.getImageElement());
    loaded.fst.setImage(image);
    entityManager.flush();
    publisher.publishEvent(new CacheEvictionEvent(id, loaded.snd, this));
  }

  @Override
  public ImageElement getIcon(Class<?> type, Identifier id) {
    return registrations.imageElement(load(id, type).fst.getImage());
  }

  private Pair<ImageAware, Class<? extends ImageAware>> load(Identifier id, Class<?> type) {
    if (!ImageAware.class.isAssignableFrom(type)) {
      throw new IllegalStateException("Can't change the image of a type that doesn't have one");
    }
    val o = (ImageAware) entityManager.find(type, id);
    if (o == null) {
      throw new EntityNotFoundException("No image aware with that id");
    }
    permissionsService.checkPermission(o, BasePermission.WRITE);
    return Pair.of(o, o.getClass());
  }
}
