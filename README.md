# mybatis通用mapper

基于MyBatis和PageHelper，提供了增、删、改、查、分页的通用mapper。

**暂未集成Java Persistence API，表必须以id为主键。**

## InsertMapper

insert mapper，只插入非null属性。

## DeleteMapper

delete mapper，使用id属性作为条件。

## UpdateMapper

update mapper，使用id属性作为条件。

## SelectMapper

select mapper，使用所有非null属性作为条件，支持分页、排序。

## PagedMapper

paged mapper，基于PageHelper进行分页、排序。

## BaseMapper

结合了增、删、改、查、分页功能的mapper。