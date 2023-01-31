package Backend.teampple.domain.tasks;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.feedbacks.repository.FeedbackOwnerRespository;
import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.feedbacks.dto.response.GetFeedbackDto;
import Backend.teampple.domain.files.repository.FilesRepository;
import Backend.teampple.domain.files.dto.response.GetFileInfoDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.tasks.dto.TaskDto;
import Backend.teampple.domain.tasks.dto.response.GetTaskDto;
import Backend.teampple.domain.tasks.entity.Operator;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.OperatorRepository;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.common.validation.dto.UserStageDto;
import Backend.teampple.global.common.validation.dto.UserTaskDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;

    private final FilesRepository filesRepository;

    private final OperatorRepository operatorRepository;

    private final FeedbackRepository feedbackRepository;

    private final FeedbackOwnerRespository feedbackOwnerRespository;

    private final StagesRepository stagesRepository;

    private final TeammateRepository teammateRepository;

    private final TeammateRepository teammateRepository;

    private final CheckUser checkUser;

    @Transactional
    public GetTaskDto getTask(String authUser, Long taskId) {
        // 1. task 조회 및 유저 관한 확인
        UserTaskDto userTaskDto = checkUser.checkIsUserHaveAuthForTask(authUser, taskId);
        Task task = userTaskDto.getTask();
        User user = userTaskDto.getUser();

        // 2. file 조회
        List<GetFileInfoDto> getFileInfoDtos = filesRepository.findAllByTaskOrderByUpdatedAt(task).stream()
                .map(GetFileInfoDto::new).collect(Collectors.toList());

        // 3. operator 조회 유저 프로파일 패치조인
        List<String> operators = operatorRepository.findAllByTaskWithUserProfile(task).stream()
                .map(o -> o.getUserProfile().getName()).collect(Collectors.toList());

        // 4. feedback + adviser + adviserProfile
        List<Feedback> feedbacks = feedbackRepository.findByTaskWithUserAndUserProfile(task);
        List<GetFeedbackDto> getFeedbackDtos = feedbacks.stream()
                .map(GetFeedbackDto::new).collect(Collectors.toList());

        // 5. feedbackOwner ischecked update
        List<FeedbackOwner> feedbackOwners = feedbackOwnerRespository.findAllByUserAndFeedback(user, feedbacks);
        feedbackOwners.stream()
                .filter(feedbackOwner -> !feedbackOwner.isChecked())
                .forEach(FeedbackOwner::updateCheckStatus);

        return GetTaskDto.builder()
                .taskName(task.getName())
                .isDone(task.isDone())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .stageName(task.getStage().getTaskName())
                .sequenceNum(task.getStage().getSequenceNum())
                .operators(operators)
                .files(getFileInfoDtos)
                .feedbacks(getFeedbackDtos)
                .build();

    };

    @Transactional
    public void postTask(String authUser, TaskDto taskDto, Long stageId) {
        // 1. stage + user + 유저 검증
        UserStageDto userStageDto = checkUser.checkIsUserCanPostTask(authUser, stageId);

        // 2. Task 생성
        Task task = Task.builder()
                .name(taskDto.getName())
                .startDate(taskDto.getStartDate())
                .dueDate(taskDto.getDueDate())
                .stage(userStageDto.getStage())
                .build();
        tasksRepository.save(task);

        // 3. operator user 불러오기
        List<Teammate> teammates = teammateRepository.findAllById(taskDto.getOperators());

        // 4. operator 생성
        teammates.forEach(teammate -> {
            Operator operator = Operator.builder()
                    .task(task)
                    .user(teammate.getUser())
                    .userProfile(teammate.getUserProfile())
                    .build();
            operatorRepository.save(operator);
        });

        // 5. stage total 늘리기
        Stage stage = userStageDto.getStage();
        stage.increaseTotalTask(1);
        stagesRepository.save(stage);
    }

    @Transactional
    public void putTask(String authUser, TaskDto taskDto, Long taskId) {
        // 1. task 조회 및 유저 관한 확인
        Task task = checkUser.checkIsUserHaveAuthForTask(authUser, taskId).getTask();

        // 2. operator 조회
        List<Operator> operators = operatorRepository.findAllByTaskWithUserOrderByUserId(task);
        List<User> curUsers = operators.stream()
                .map(Operator::getUser)
                .collect(Collectors.toList());

        // 3. taskDto
        List<Teammate> teammates = teammateRepository.findAllByIdOrderByUserId(taskDto.getOperators());
        List<User> newUsers = teammates.stream()
                .map(Teammate::getUser)
                .collect(Collectors.toList());

        // 3.1 삭제
        operators.stream()
                .filter(operator -> !newUsers.contains(operator.getUser()))
                .forEach(operatorRepository::delete);
        // 3.2 추가
        newUsers.stream()
                .filter(newUser -> !curUsers.contains(newUser))
                .forEach(newUser -> {
                    Operator operator = Operator.builder()
                            .user(newUser)
                            .userProfile(newUser.getUserProfile())
                            .task(task)
                            .build();
                    operatorRepository.save(operator);
                });

        // 4. task update
        task.update(taskDto);
        tasksRepository.save(task);
    }

    public void deleteTask(String authUser, Long taskId) {
        // 1. task 조회 및 유저 관한 확인
        Task task = checkUser.checkIsUserHaveAuthForTask(authUser, taskId).getTask();

        // 2. 삭제
        tasksRepository.delete(task);

        // 3. stage totaltask 변경
        Stage stage = task.getStage();
        stage.decreaseTotalTask(1);
        stagesRepository.save(stage);
    }

    public void getConvertStatus(String authUser, Long taskId) {
        // 1. task 조회 및 유저 관한 확인
        Task task = checkUser.checkIsUserHaveAuthForTask(authUser, taskId).getTask();

        // 2. task status convert
        task.convertStatus();
        tasksRepository.save(task);

        // 3. stage achievement 변경
        Stage stage = task.getStage();
        if (task.isDone()) {
            stage.increaseAchievement(1);
        } else {
            stage.decreaseAchievement(1);
        }
        stagesRepository.save(stage);
    }
}
