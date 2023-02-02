package Backend.teampple.domain.feedbacks;

import Backend.teampple.domain.feedbacks.dto.request.PostFeedbackDto;
import Backend.teampple.domain.feedbacks.dto.request.PutFeedbackDto;
import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.feedbacks.repository.FeedbackOwnerRespository;
import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.tasks.entity.Operator;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.OperatorRepository;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbacksService {

    private final FeedbackRepository feedbackRepository;

    private final TasksRepository tasksRepository;

    private final OperatorRepository operatorRepository;

    private final FeedbackOwnerRespository feedbackOwnerRespository;

    private final UserRepository userRepository;

    private final EntityManager em;

    private final CheckUser checkUser;

    @Transactional
    public void postFeedback(User authUser, PostFeedbackDto postFeedbackDto, Long taskId) {
//        User user = userRepository.findById(authUser.getId())
//                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));
//        log.info("{}",user);
        // 1. task 조회
        Task task = tasksRepository.findByIdWithStage(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. 유저 체크 및 유저 불러오기
        checkUser.checkIsUserCanPostFeedback(authUser, task.getStage().getTeam());

        // 3. 피드백 생성
        Feedback feedback = Feedback.builder()
                .comment(postFeedbackDto.getComment())
                .adviser(authUser)
                .task(task)
                .build();
        feedbackRepository.save(feedback);


        // 3. 피드백 오너 생성 유저, 유저 프로파일 패치조인
        List<Operator> operators = operatorRepository.findAllByTask(task);
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
    public void putFeedback(User authUser, PutFeedbackDto putFeedbackDto, Long feedbackId) {
        // 1. 피드백 조회 및 권한 확인
        Feedback feedback = checkUser.checkIsUserCanModifyFeedback(authUser, feedbackId);

        // 2. update
        feedback.update(putFeedbackDto);
        feedbackRepository.save(feedback);
    }

    @Transactional
    public void deleteFeedback(User authUser, Long feedbackId) {
        // 1. 피드백 조회 및 권한 확인
        Feedback feedback = checkUser.checkIsUserCanModifyFeedback(authUser, feedbackId);

        // 2. feedbackOwner 삭제
        feedbackOwnerRespository.deleteAllByFeedback(feedback);

        // 3. feedback 삭제
        feedbackRepository.delete(feedback);
    }
}
