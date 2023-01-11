package Backend.teampple.domain.feedbacks;

import Backend.teampple.domain.feedbacks.dto.request.PostFeedbackDto;
import Backend.teampple.domain.feedbacks.dto.request.PutFeedbackDto;
import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.feedbacks.repository.FeedbackOwnerRespository;
import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.files.dto.response.GetFileDto;
import Backend.teampple.domain.tasks.entity.Operator;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.OperatorRepository;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.common.response.CommonResponse;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbacksService {

    private final FeedbackRepository feedbackRepository;

    private final TasksRepository tasksRepository;

    private final OperatorRepository operatorRepository;

    private final FeedbackOwnerRespository feedbackOwnerRespository;

    private final UserRepository userRepository;

    @Transactional
    public void postFeedback(PostFeedbackDto postFeedbackDto, Long taskId) {
        // 1. task 조회
        Task task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // -------------------------------
        // temp 유저
        User temp = userRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));
        // -------------------------------

        // 2. 피드백 생성
        Feedback feedback = Feedback.builder()
                .comment(postFeedbackDto.getComment())
                .adviser(temp) // 여기 유저
                .task(task)
                .build();
        feedbackRepository.save(feedback);

        // 3. 피드백 오너 생성 유저, 유저 프로파일 패치조인
        List<Operator> operators = operatorRepository.findAllWithUserAndUserProfileByTask(task);
        operators.forEach(operator -> {
                    FeedbackOwner feedbackOwner = FeedbackOwner.builder()
                            .user(operator.getUser())
                            .userProfile(operator.getUserProfile())
                            .feedback(feedback)
                            .build();
                    feedbackOwnerRespository.save(feedbackOwner);
                });

    }

    @Transactional
    public void putFeedback(PutFeedbackDto putFeedbackDto, Long feedbackId) {
        // 1. task 조회
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEEDBACK_NOT_FOUND.getMessage()));

        // 2. update
        feedback.update(putFeedbackDto);
        feedbackRepository.save(feedback);
    }

    @Transactional
    public void deleteFeedback(Long feedbackId) {
        // 1. feedback 조회
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEEDBACK_NOT_FOUND.getMessage()));

        // 2. feedbackOwner 삭제
        feedbackOwnerRespository.deleteAllByFeedback(feedback);

        // 3. feedback 삭제
        feedbackRepository.delete(feedback);
    }
}
