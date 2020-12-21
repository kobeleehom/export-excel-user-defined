
# batch export excel

分页查询并写入excel
1.分批次查询写入文件，避免大批量一次查询写入带来的瓶颈
2.方法抽象，加入依赖包后实现对应的IExportService接口即可
3.配置可空，支持相同数据的单元格合并等

## 技术栈

- springboot
- easyExcel
- 使用MyBatisPlus作为持久层

## 环境

- JDK1.8

## 启动
内附 demo 使用样例
