package pers.wayease.duolaimall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeValueDto;
import pers.wayease.duolaimall.product.pojo.param.PlatformAttributeInfoParam;
import pers.wayease.duolaimall.product.service.PlatformAttributeService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller
 * @name AdminPlatformAttributeController
 * @description Admin platform attribute controller class.
 * @since 2024-10-08 09:10
 */
@RestController
@RequestMapping("/admin/product")
public class AdminPlatformAttributeController {

    @Autowired
    private PlatformAttributeService platformAttributeService;

    @PostMapping("/saveAttrInfo")
    public Result<Void> savePlatformAttributeInfo(@RequestBody PlatformAttributeInfoParam platformAttributeInfoParam) {
        platformAttributeService.savePlatformAttributeInfo(platformAttributeInfoParam);
        return Result.ok();
    }

    @GetMapping("/attrInfoList/{firstLevelCategoryId}/{secondLevelCategoryId}/{thirdLevelCategoryId}")
    public Result<List<PlatformAttributeInfoDto>> getPlatformAttributeInfoList(
            @PathVariable Long firstLevelCategoryId,
            @PathVariable Long secondLevelCategoryId,
            @PathVariable Long thirdLevelCategoryId
    ) {
        List<PlatformAttributeInfoDto> platformAttributeInfoDtoList = platformAttributeService.getPlatformAttributeInfoList(firstLevelCategoryId, secondLevelCategoryId, thirdLevelCategoryId);
        return Result.ok(platformAttributeInfoDtoList);
    }

    @GetMapping("/getAttrValueList/{platformAttributeId}")
    public Result<List<PlatformAttributeValueDto>> getPlatformAttributeValueList(@PathVariable Long platformAttributeId) {
        List<PlatformAttributeValueDto> platformAttributeValueDtoList = platformAttributeService.getPlatformAttributeValueList(platformAttributeId);
        return Result.ok(platformAttributeValueDtoList);
    }
}
