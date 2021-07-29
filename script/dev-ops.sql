查看DB表大小
select  TABLE_NAME,  concat(truncate(data_length/1024/1024,2),' MB') as data_size, concat(truncate(index_length/1024/1024,2),' MB') as index_size from information_schema.tables  where TABLE_SCHEMA = 'cat' group by TABLE_NAME order by data_length desc;



查看表分区信息

SELECT PARTITION_NAME, PARTITION_METHOD, PARTITION_EXPRESSION, PARTITION_DESCRIPTION, TABLE_ROWS
	, SUBPARTITION_NAME, SUBPARTITION_METHOD, SUBPARTITION_EXPRESSION
FROM information_schema.PARTITIONS
WHERE TABLE_SCHEMA = SCHEMA()
	AND TABLE_NAME = 'report_content';