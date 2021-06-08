package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.model.*;
import com.qexz.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/manage")
public class ManageController {

    private static Log LOG = LogFactory.getLog(ManageController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ExaminationPaperService examinationPaperService;
    @Autowired
    private ExaminationPaperDetailService examinationPaperDetailService;

    @Autowired
    private ExaminationAnswerService examinationAnswerService;

    @Autowired
    private ExaminationAnswerDetailService examinationAnswerDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionTypeService positionTypeService;

    /**
     * 管理员登录页
     */
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login(HttpServletRequest request,  Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);

        if (currentAccount == null) {
            return "manage/manage-login";
        } else {
            return "redirect:/manage/paper/list";
        }
    }

    /**
     * 用户管理
     */
    @RequestMapping(value="/account/list", method= RequestMethod.GET)
    public String accountList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = accountService.getAccounts(page, QexzConst.accountPageSize);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-accountList";
        }
    }

    /**
     * 用户管理
     */
    @RequestMapping(value="/user/list", method= RequestMethod.GET)
    public String userList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = userService.getUsers(page, QexzConst.accountPageSize);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-userList";
        }
    }
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = QexzConst.UPLOAD_FILE_IMAGE_PATH;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            User currentAccount = (User) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            currentAccount.setResume_file(filePath + fileName);
            userService.updateUser(currentAccount);
            LOG.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            LOG.error(e.toString(), e);
        }
        return "上传失败！";
    }


    /**
     * 考试管理
     */
    @RequestMapping(value="/contest/list", method= RequestMethod.GET)
    public String contestList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
            List<Position> positions = positionService.getPositions();
            data.put("positions", positions);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-contestBoard";
        }
    }

    /**
     * 考试管理-查看试题
     */
    @RequestMapping(value="/contest/{contestId}/problems", method= RequestMethod.GET)
    public String contestProblemList(HttpServletRequest request,
                                     @PathVariable("contestId") Integer contestId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = new HashMap<>();
            List<Question> questions = questionService.getQuestionsByContestId(contestId);
            List<Department> departments =departmentService.getDepartments();
            Contest contest = contestService.getContestById(contestId);
            data.put("questionsSize", questions.size());
            data.put("departments", departments);
            data.put("questions", questions);
            data.put("contest", contest);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-editContestProblem";
        }
    }

    /**
     * 考试管理-查看试题
     */
    @RequestMapping(value="/paper/{paperId}/questions", method= RequestMethod.GET)
    public String paperProblemList(HttpServletRequest request,
                                     @PathVariable("paperId") Integer paperId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = new HashMap<>();
            List<ExaminationPaperDetail> examinationPaperDetails = examinationPaperDetailService.getExaminationPaperDetailsByPaperId(paperId);
            ExaminationPaper examinationPaper = examinationPaperService.getExaminationPaperById(paperId);
            List<Question> questions = questionService.getQuestions();
            for(ExaminationPaperDetail examinationPaperDetail:examinationPaperDetails){
                Question question = questionService.getQuestionById(examinationPaperDetail.getQuestion_id());
                examinationPaperDetail.setQuestion(question);
            }
            data.put("examinationPaperDetailsSize", examinationPaperDetails.size());
            data.put("examinationPaperDetails", examinationPaperDetails);
            data.put("examinationPaper", examinationPaper);
            data.put("questions", questions);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-editExaminationPaperDetail";
        }
    }

    /**
     * 題目管理
     */
    @RequestMapping(value="/question/list", method= RequestMethod.GET)
    public String questionList(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "content", defaultValue = "") String content,
                               Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = questionService.getQuestionsByContent(page,
                    QexzConst.questionPageSize, content);
            List<Question> questions = (List<Question>) data.get("questions");
            List<Position> positions = positionService.getPositions();
            Map<Integer, String> positionId2name = positions.stream().
                    collect(Collectors.toMap(Position::getId, Position::getName));
            for (Question question : questions) {
                question.setPositionName(positionId2name.
                        getOrDefault(question.getpositionId(), "未知科目"));
            }
            data.put("positions", positions);
            data.put("content", content);
            model.addAttribute("data", data);
            return "manage/manage-questionBoard";
        }
    }

    /**
     * 成绩管理-考试列表
     */
    @RequestMapping(value="/result/contest/list", method= RequestMethod.GET)
    public String resultContestList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
            List<Position> positions = positionService.getPositions();
            data.put("positions", positions);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-resultContestBoard";
        }
    }


    /**
     * 成绩管理-考试详情列表
     */
    @RequestMapping(value="/examAnswer/list", method= RequestMethod.GET)
    public String examAnswerList(HttpServletRequest request,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> retdata = new HashMap<>();
            List<ExaminationAnswer> answers = new ArrayList<ExaminationAnswer>();
            Map<String, Object> data =examinationAnswerService.getExaminationAnswers(page, QexzConst.contestPageSize);
            List<ExaminationAnswer> examinationAnswers = (List<ExaminationAnswer>) data.get("examinationAnswers");
            List<ExaminationPaper> papers = examinationPaperService.getExaminationPapers();
            List<User> users = userService.getApprovedUsers();
            for(ExaminationAnswer examinationAnswer : examinationAnswers){
                ExaminationPaper examinationPaper = examinationPaperService.getExaminationPaperById(examinationAnswer.getPaper_id());
                User user = userService.getUserById(examinationAnswer.getUser_id());
                examinationAnswer.setPaper(examinationPaper);
                examinationAnswer.setUser(user);
                answers.add(examinationAnswer);
            }
            retdata.put("examinationAnswersSize",answers.size());
            retdata.put("examinationAnswers", answers);
            retdata.put("papers", papers);
            retdata.put("users", users);
            model.addAttribute(QexzConst.DATA, retdata);
            return "manage/manage-examAnswerBoard";
        }
    }
    /**
     * 成绩管理-考试列表-学生成绩列表
     */
    @RequestMapping(value="/result/contest/{contestId}/list", method= RequestMethod.GET)
    public String resultStudentList(HttpServletRequest request,
                                    @PathVariable("contestId") int contestId,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = new HashMap<>();
            List<Grade> grades = gradeService.getGradesByContestId(contestId);
            Contest contest = contestService.getContestById(contestId);
            List<Question> questions = questionService.getQuestionsByContestId(contestId);
            List<Integer> studentIds = grades.stream().map(Grade::getStudentId).collect(Collectors.toList());
            List<Account> students = accountService.getAccountsByStudentIds(studentIds);
            Map<Integer, Account> id2student = students.stream().
                    collect(Collectors.toMap(Account::getId, account -> account));
            for (Grade grade : grades) {
                Account student = id2student.get(grade.getStudentId());
                grade.setStudent(student);
            }
            data.put("grades", grades);
            data.put("contest", contest);
            data.put("questions", questions);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-resultStudentBoard";
        }
    }

    /**
     * 职位管理
     */
    @RequestMapping(value="/position/list", method= RequestMethod.GET)
    public String positionList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {

            Map<String, Object> retdata = new HashMap<>();
            List<Position> positions = new ArrayList<Position>();
            Map<String, Object> data =positionService.getPositions(page, QexzConst.positionPageSize);
            List<Department> departments = departmentService.getDepartments();
            positions = (List<Position>) data.get("positions");
            List<PositionType> positionTypes = positionTypeService.getPositionTypes();
            for(Position position : positions){
                Department department = departmentService.getDepartmentById(position.getDepartment_id());
                PositionType positionType  = positionTypeService.getPositionTypeById(position.getPosi_type());
                position.setDepartment(department);
                position.setPositionType(positionType);
            }
            retdata.put("positionsSize",positions.size());
            retdata.put("positions", positions);
            retdata.put("positionTypes", positionTypes);
            retdata.put("departments", departments);
            model.addAttribute(QexzConst.DATA, retdata);
            return "manage/manage-positionBoard";
        }
    }

    /**
     * 帖子管理
     */
    @RequestMapping(value="/post/list", method= RequestMethod.GET)
    public String postList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = postService.getPosts(page, QexzConst.postPageSize);
            List<Post> posts = (List<Post>) data.get("posts");
            Set<Integer> authorIds = posts.stream().map(Post::getAuthorId).collect(Collectors.toCollection(HashSet::new));
            List<Account> authors = accountService.getAccountsByIds(authorIds);
            Map<Integer, Account> id2author = authors.stream().
                    collect(Collectors.toMap(Account::getId, account -> account));
            for (Post post : posts) {
                post.setAuthor(id2author.get(post.getAuthorId()));
            }
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-postBoard";
        }
    }

    /**
     * 评论管理
     */
    @RequestMapping(value="/comment/list", method= RequestMethod.GET)
    public String commentList(HttpServletRequest request,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = commentService.getComments(page, QexzConst.commentPageSize);
            List<Comment> comments = (List<Comment>) data.get("comments");
            Set<Integer> userIds = comments.stream().map(Comment::getUserId).collect(Collectors.toCollection(HashSet::new));
            List<Account> users = accountService.getAccountsByIds(userIds);
            Map<Integer, Account> id2user = users.stream().
                    collect(Collectors.toMap(Account::getId, account -> account));
            for (Comment comment : comments) {
                comment.setUser(id2user.get(comment.getUserId()));
            }
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-commentBoard";
        }
    }

    /**
     * 考卷管理
     */
    @RequestMapping(value="/paper/list", method= RequestMethod.GET)
    public String paperList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            return "error/404";
        } else {
            Map<String, Object> retdata = new HashMap<>();
            Map<String, Object> data = examinationPaperService.getPagesExaminationPapers(page, QexzConst.contestPageSize);
            List<ExaminationPaper> papers = (List<ExaminationPaper>) data.get("papers");
            List<Department> departments = departmentService.getDepartments();
            for(ExaminationPaper paper : papers){
                Department department = departmentService.getDepartmentById(paper.getDepartment_id());
                paper.setDepartment(department);
            }
            retdata.put("papersSize",papers.size());
            retdata.put("papers", papers);
            retdata.put("departments", departments);
            model.addAttribute(QexzConst.DATA, retdata);
            return "manage/manage-paperBoard";
        }
    }

    /**
     * 部门管理
     */
    @RequestMapping(value="/department/list", method= RequestMethod.GET)
    public String departmentList(HttpServletRequest request,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            return "error/404";
        } else {
            Map<String, Object> data = departmentService.getDepartments(page, QexzConst.contestPageSize);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-departmentBoard";
        }
    }


    /**
     * 成绩管理-考试列表-答卷明细
     */
    @RequestMapping(value="/answerDetail/list/{answerId}", method= RequestMethod.GET)
    public String answerDetailList(HttpServletRequest request,
                                    @PathVariable("answerId") int answerId,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = new HashMap<>();
            ExaminationAnswer examinationAnswer = examinationAnswerService.getExaminationAnswerById(answerId);
            ExaminationPaper paper = examinationPaperService.getExaminationPaperById(examinationAnswer.getPaper_id());
            examinationAnswer.setPaper(paper);
            User user = userService.getUserById(examinationAnswer.getUser_id());
            examinationAnswer.setUser(user);
            List<ExaminationAnswerDetail> examinationAnswerDetails = examinationAnswerDetailService.getExaminationAnswerDetailByAnswerId(answerId);

            for(ExaminationAnswerDetail examinationAnswerDetail :examinationAnswerDetails){
                Question question = questionService.getQuestionById(examinationAnswerDetail.getQuestion_id());
                examinationAnswerDetail.setQuestion(question);
            }
            data.put("answer", examinationAnswer);
            data.put("answerDetails", examinationAnswerDetails);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-answerDetailBoard";
        }
    }

    /**
     * 职位类型管理
     */
    @RequestMapping(value="/positiontype/list", method= RequestMethod.GET)
    public String positionTypeList(HttpServletRequest request,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_MANAGE_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            return "error/404";
        } else {
            Map<String, Object> data = positionTypeService.getPositionTypes(page, QexzConst.contestPageSize);
            model.addAttribute(QexzConst.DATA, data);
            return "manage/manage-positionTypeBoard";
        }
    }
}
