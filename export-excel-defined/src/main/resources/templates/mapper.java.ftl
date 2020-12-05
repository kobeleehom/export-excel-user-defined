package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;

/**
 * ${table.comment!} Mapper 接口 <br>
 *
 * @author ${author} <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate ${date} <br>
 * @see ${package.Mapper} <br>
 */
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
