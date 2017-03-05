package com.dtrade.repository.categorytick;

import com.dtrade.model.categorytick.CategoryTick;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kudelin on 3/5/17.
 */
public interface CategoryTickRepository extends JpaRepository<CategoryTick, Long> {
}
