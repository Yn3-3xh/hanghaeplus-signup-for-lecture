package hanghaeplus.signupforlecture.lecture.api.controller;

import hanghaeplus.signupforlecture.lecture.api.dto.LectureApplyRequestDto;
import hanghaeplus.signupforlecture.lecture.api.dto.LectureResponseDto;
import hanghaeplus.signupforlecture.lecture.api.dto.request.LectureAvailableRequestDto;
import hanghaeplus.signupforlecture.lecture.application.facade.LectureFacade;
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
    public ResponseEntity<List<LectureResponseDto>> getAvailableLectures(
            @Valid @RequestBody LectureAvailableRequestDto lectureAvailableRequestDto) {

        List<LectureResponseDto> availableLectures = lectureFacade.getAvailableLectures(lectureAvailableRequestDto);
        return ResponseEntity.ok(availableLectures);
    }

    @GetMapping("/{userId}/signed-up")
    public ResponseEntity<List<LectureResponseDto>> getSignedUpLectures(
            @PathVariable Long userId) {

        List<LectureResponseDto> signedUpLectures = lectureFacade.getSignedUpLectures(userId);
        return ResponseEntity.ok(signedUpLectures);
    }

    @PostMapping("/{lectureId}/apply")
    public ResponseEntity<?> applyLecture(
            @PathVariable Long lectureId,
            @Valid @RequestBody LectureApplyRequestDto lectureApplyRequestDto) {

        lectureFacade.applyLecture(lectureId, lectureApplyRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Lecture applied successfully");
    }
}
