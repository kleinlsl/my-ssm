show tables;
select count(*) as "发放红包量" from wx_drainage_detail where receive=1;
select member_id as "会员id", count(member_id) as "领卷数量" from wx_drainage_detail where receive=1 group by member_id;
select count(*) as "引流会员量", sum(receive_count) as "发放红包总量"
from (
         select member_id, count(activity_code) as receive_count
         from wx_drainage_detail
         where receive = 1
         group by member_id
     ) as tem_member;


select count(distinct member_id)
  from wx_drainage_detail
         where receive = 1
