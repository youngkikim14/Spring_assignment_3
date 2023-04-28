package com.sparta.assignment.service;

import com.sparta.assignment.dto.CommentRequestDto;
import com.sparta.assignment.entity.Comment;
import com.sparta.assignment.entity.User;
import com.sparta.assignment.entity.UserRoleEnum;
import com.sparta.assignment.jwt.JwtUtil;
import com.sparta.assignment.repository.CommentRepository;
import com.sparta.assignment.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String createComment(CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request); //토큰값
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token); //토큰으로 사용자 정보 가져오기
            } else {
                throw new IllegalArgumentException("Token Erro"); // 에러표시
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow( //토큰이 맞으면 토큰으로 db에서 사용자 정보 조회
                    () -> new IllegalArgumentException("없는 유저입니다")
            );
            Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, user));

            return "댓글 저장 완료";
        } else {
            return null;
        }
    }

    @Transactional
    public String updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request); //토큰값
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token); //토큰으로 사용자 정보 가져오기
            } else {
                throw new IllegalArgumentException("Token Erro"); // 에러표시
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow( //토큰이 맞으면 토큰으로 db에서 사용자 정보 조회
                    () -> new IllegalArgumentException("없는 유저입니다")
            );
            Comment comment = commentRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow( // 없는 글 null 처리
                    () -> new NullPointerException("존재하지 않은 게시글입니다.")
            );
            UserRoleEnum userRoleEnum = user.getUserRoleEnum();
            if (userRoleEnum == UserRoleEnum.USER || userRoleEnum == UserRoleEnum.ADMIN) {
                comment.updateComment(commentRequestDto, user);
                return "업데이트 완료";
            } else {
                throw new IllegalArgumentException("권한이 없습니다");
            }
        } else {
            return null;
        }
    }

    @Transactional
    public String deleteComment(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request); //토큰값
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token); //토큰으로 사용자 정보 가져오기
            } else {
                throw new IllegalArgumentException("Token Erro"); // 에러표시
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow( //토큰이 맞으면 토큰으로 db에서 사용자 정보 조회
                    () -> new IllegalArgumentException("없는 유저입니다")
            );
            Comment comment = commentRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow( // 없는 글 null 처리
                    () -> new NullPointerException("존재하지 않은 게시글입니다.")
            );
            UserRoleEnum userRoleEnum = user.getUserRoleEnum();
            if (userRoleEnum == UserRoleEnum.USER || userRoleEnum == UserRoleEnum.ADMIN) {
                commentRepository.deleteById(id);
                return "댓글이 삭제 되었습니다";
            }
        } else {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return null;
    }
}