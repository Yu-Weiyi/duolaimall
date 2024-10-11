package pers.wayease.duolaimall.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pers.wayease.duolaimall.search.pojo.model.Goods;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.repository
 * @name GoodsRepository
 * @description Goods repository interface.
 * @since 2024-10-10 16:13
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {}
