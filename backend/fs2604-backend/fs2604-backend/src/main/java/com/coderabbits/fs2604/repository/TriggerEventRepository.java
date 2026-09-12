package com.coderabbits.fs2604.repository;

import com.coderabbits.fs2604.model.TriggerEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TriggerEventRepository extends JpaRepository<TriggerEvent, Long> {
    Optional<TriggerEvent> findByEventUuid(String eventUuid);
    List<TriggerEvent> findByDistrictIgnoreCaseOrderByReadingDateDesc(String district);
    List<TriggerEvent> findByTriggeredTrueOrderByEvaluatedAtDesc();
}
