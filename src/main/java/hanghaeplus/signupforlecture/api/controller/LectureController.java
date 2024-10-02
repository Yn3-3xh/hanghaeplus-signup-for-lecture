package hanghaeplus.signupforlecture.api.controller;

import hanghaeplus.signupforlecture.api.dto.request.LectureAvailableRequest;
import hanghaeplus.signupforlecture.api.dto.response.LectureSignedUpResponse;
import hanghaeplus.signupforlecture.application.lecture.facade.LectureFacade;
import hanghaeplus.signupforlecture.api.dto.request.LectureApplyRequest;
import hanghaeplus.signupforlecture.api.dto.response.LectureAvailableResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    @GetMapping("/available")
    public ResponseEntity<List<LectureAvailableResponse>> getAvailableLectures(
            @Valid @RequestBody LectureAvailableRequest lectureAvailableRequest) {

        List<LectureAvailableResponse> availableLectures = lectureFacade.getAvailableLectures(lectureAvailableRequest.toDto()).stream()
                .map(LectureAvailableResponse::fromDto)
                .toList();
        return ResponseEntity.ok(availableLectures);
    }

    @GetMapping("/{userId}/signed-up")
    public ResponseEntity<List<LectureSignedUpResponse>> getSignedUpLectures(
            @PathVariable Long userId) {

        List<LectureSignedUpResponse> signedUpLectures = lectureFacade.getSignedUpLectures(userId).stream()
                .map(LectureSignedUpResponse::fromDto)
                .toList();
        return ResponseEntity.ok(signedUpLectures);
    }

    @PostMapping("/{lectureId}/apply")
    public ResponseEntity<String> applyLecture(
            @PathVariable Long lectureId,
            @Valid @RequestBody LectureApplyRequest lectureApplyRequest) {

        lectureFacade.applyLecture(lectureId, lectureApplyRequest.toDto());
        return ResponseEntity.status(HttpStatus.CREATED).body("Lecture applied successfully");
    }
}
