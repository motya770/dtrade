package com.dtrade.repository.categorytick;

import com.dtrade.model.categorytick.CategoryTick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by kudelin on 3/5/17.
 */
public interface CategoryTickRepository extends JpaRepository<CategoryTick, Long> {

    @Query("select ct from CategoryTick ct where score >= :lowerBound and score < :upperBound ")
    List<CategoryTick> getByScoreBounds(@Param(value = "lowerBound") Integer lowerBound, @Param(value = "upperBound") Integer upperBound);
}
