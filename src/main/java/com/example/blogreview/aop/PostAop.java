package com.example.blogreview.aop;

import com.example.blogreview.entity.Comment;
import com.example.blogreview.entity.Post;
import com.example.blogreview.entity.User;
import com.example.blogreview.entity.UserRoleEnum;
import com.example.blogreview.repository.CommentRepository;
import com.example.blogreview.repository.PostRepository;
import com.example.blogreview.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class PostAop {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Pointcut("execution(* com.example.blogreview.service.PostService.updatePost(..))")
    private void updatePost() {}

    @Pointcut("execution(* com.example.blogreview.service.PostService.deletePost(..))")
    private void deletePost() {}

    @Pointcut("execution(* com.example.blogreview.service.CommentService.updateCommnet(..))")
    private void updateComment() {}

    @Pointcut("execution(* com.example.blogreview.service.CommentService.deleteComment(..))")
    private void deleteComment() {}

    @Around("updatePost() || deletePost()")
    public Object postRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        // 게시글 정보 가져오기
        Long postId = (Long) joinPoint.getArgs()[0];
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // 로그인 상태의 유저 정보 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) joinPoint.getArgs()[1];
        User user = userDetails.getUser();

        if (!user.getRole().equals(UserRoleEnum.ADMIN) || post.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정/삭제할 수 있습니다.");
        }

        return joinPoint.proceed();
    }

    @Around("updateComment() || deleteComment()")
    public Object commentRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        // 댓글 정보 받아오기
        Long blogId = (Long) joinPoint.getArgs()[0];
        Long commentId = (Long) joinPoint.getArgs()[1];
        Comment comment = commentRepository.findByIdAndPostId(commentId, blogId);

        // 로그인된 유저 정보 받아오기
        UserDetailsImpl userDetails = (UserDetailsImpl) joinPoint.getArgs()[2];
        User user = userDetails.getUser();

        if(comment == null) throw new IllegalArgumentException("해당 댓글이 존재하지 않습니다.");

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || comment.getUser().getUsername().equals(user.getUsername()))) {
            throw new IllegalArgumentException("자신이 등록한 댓글만 수정/삭제할 수 있습니다.");
        }

        return joinPoint.proceed();
    }

    @Pointcut("execution(public * com.example.blogreview.post.controller..*(..))")
    private void allPostAop(){}

    @Before("allPostAop()")
    public void postNameBefore(JoinPoint joinPoint){
        //실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName() + "의 AOP 동작");
    }

    @AfterReturning("allPostAop()")
    public void postNameAfterReturning(JoinPoint joinPoint){
        //실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName() + "의 AOP 동작");
    }
}
