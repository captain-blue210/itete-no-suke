package app.itetenosuke.api.presentation.controller.bodypart;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.itetenosuke.api.application.bodypart.BodyPartUseCase;
import app.itetenosuke.api.domain.user.UserDetailsImpl;
import app.itetenosuke.api.presentation.controller.shared.BodyPartReqBody;
import app.itetenosuke.api.presentation.controller.shared.BodyPartResBody;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BodyPartController {
  private final BodyPartUseCase bodyPartUseCase;

  @GetMapping(path = "/v1/bodyparts", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<BodyPartResBody> getBodyPartList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return bodyPartUseCase
        .getBodyPartList(userDetails.getUserId())
        .stream()
        .map(v -> BodyPartResBody.of(v))
        .collect(Collectors.toList());
  }

  @GetMapping(path = "/v1/bodyparts/{bodyPartId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BodyPartResBody getBodyPart(
      @PathVariable String bodyPartId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return BodyPartResBody.of(bodyPartUseCase.getBodyPart(bodyPartId));
  }

  @PostMapping(path = "/v1/bodyparts", produces = MediaType.APPLICATION_JSON_VALUE)
  public BodyPartResBody createBodyPart(
      @RequestBody BodyPartReqBody bodyPartReqBody,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    String bodyPartId = bodyPartUseCase.createBodyPart(bodyPartReqBody);
    return BodyPartResBody.of(bodyPartUseCase.getBodyPart(bodyPartId));
  }

  @PutMapping(path = "/v1/bodyparts/{bodyPartId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BodyPartResBody updateBodyPart(
      @RequestBody BodyPartReqBody bodyPartReqBody,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    String bodyPartId = bodyPartUseCase.updateBodyPart(bodyPartReqBody);
    return BodyPartResBody.of(bodyPartUseCase.getBodyPart(bodyPartId));
  }

  @DeleteMapping(path = "/v1/bodyparts/{bodyPartId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteBodyPart(
      @PathVariable String bodyPartId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    bodyPartUseCase.deleteBodyPart(bodyPartId);
    return ResponseEntity.ok(Collections.EMPTY_MAP);
  }
}
