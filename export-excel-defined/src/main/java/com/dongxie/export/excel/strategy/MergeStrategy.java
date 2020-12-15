package com.dongxie.export.excel.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.xiaoleilu.hutool.util.StrUtil;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @version: V1.0
 * @author: dong.xie
 * @description: 合并单元格策略 建议指定每行一个默认的唯一列作为唯一标识
 **/
public class MergeStrategy extends AbstractMergeStrategy {

    private final String STR_JOIN_SEPARATOR = "-UNIQUE_SEPARATOR-";
    /**
     * 合并的列编号，从0开始，指定的index或自己按字段顺序数
     */
    private Set<Integer> mergeCellIndex = new HashSet<>();

    /**
     * 数据集大小，用于区别结束行位置
     */
    private Long maxRow = 0L;

    /**
     * 导出唯一识别列id
     */
    private Integer uniqueIndex = 0;

    /**
     * 禁止无参声明
     */
    private MergeStrategy() {
    }

    public MergeStrategy(long maxRow, Set<Integer> mergeCellIndex, Integer uniqueIndex) {
        this.mergeCellIndex = mergeCellIndex;
        this.maxRow = maxRow;
        this.uniqueIndex = uniqueIndex;
    }

    /**
     * 记录上一次合并的信息
     */
    private final Map<Integer, MergeRange> lastRow = new HashedMap();

    /**
     * 每行每列都会进入，绝对不要在这写循环
     */
    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        int currentCellIndex = cell.getColumnIndex();
        // 判断该列是否需要合并
        if (mergeCellIndex.contains(currentCellIndex)) {
            // 用第一列的 编码值 和当前单元格的值用 - 拼接作为唯一识别
            Cell cellUnique = cell.getRow().getCell(uniqueIndex);
            String currentCellValue = StrUtil.join(STR_JOIN_SEPARATOR, cellUnique.getStringCellValue(), cell.getStringCellValue());

            int currentRowIndex = cell.getRowIndex();
            if (!lastRow.containsKey(currentCellIndex)) {
                // 记录首行起始位置
                lastRow.put(currentCellIndex, new MergeRange(currentCellValue, currentRowIndex, currentRowIndex, currentCellIndex, currentCellIndex));
                return;
            }
            //有上行这列的值了，拿来对比.
            MergeRange mergeRange = lastRow.get(currentCellIndex);
            if (!(mergeRange.lastValue != null && mergeRange.lastValue.equals(currentCellValue))) {
                // 结束的位置触发下合并.
                // 同行同列不能合并，会抛异常
                if (mergeRange.startRow != mergeRange.endRow || mergeRange.startCell != mergeRange.endCell) {
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(mergeRange.startRow, mergeRange.endRow, mergeRange.startCell, mergeRange.endCell));
                }
                // 更新当前列起始位置
                lastRow.put(currentCellIndex, new MergeRange(currentCellValue, currentRowIndex, currentRowIndex, currentCellIndex, currentCellIndex));
            }
            // 合并行 + 1
            mergeRange.endRow += 1;
            // 结束的位置触发下最后一次没完成的合并
            if (relativeRowIndex.equals(Long.valueOf(maxRow).intValue() - 1)) {
                MergeRange lastMergeRange = lastRow.get(currentCellIndex);
                // 同行同列不能合并，会抛异常
                if (lastMergeRange.startRow != lastMergeRange.endRow || lastMergeRange.startCell != lastMergeRange.endCell) {
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(lastMergeRange.startRow, lastMergeRange.endRow, lastMergeRange.startCell, lastMergeRange.endCell));
                }
            }
        }
    }
}

class MergeRange {
    public int startRow;
    public int endRow;
    public int startCell;
    public int endCell;
    public String lastValue;

    public MergeRange(String lastValue, int startRow, int endRow, int startCell, int endCell) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCell = startCell;
        this.endCell = endCell;
        this.lastValue = lastValue;
    }
}
