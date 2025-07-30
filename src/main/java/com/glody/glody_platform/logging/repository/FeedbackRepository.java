package com.glody.glody_platform.logging.repository;

import com.glody.glody_platform.logging.entity.Feedback;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findBySender(User sender);
    List<Feedback> findByReceiver(User receiver);
    List<Feedback> findBySenderOrReceiver(User sender, User receiver);
}