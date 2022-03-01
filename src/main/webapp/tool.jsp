<%@ page contentType="text/html;charset=gb2312" %>
<html>
<head>
    <script type="text/javascript" src="jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#getParams").click(function () {
                showDivFun();
            });
            changeColor('simpleTools');
        });

        //弹出调用的方法
        function showDivFun() {
            document.getElementById('params').style.display = 'block';
        }//关闭事件
        function closeDivFun() {
            document.getElementById('params').style.display = 'none';
        }

        function changeColor(id) {
            if (document.getElementsByTagName) {
                var table = document.getElementById(id);
                var rows = table.getElementsByTagName("tr");

                for (i = 1; i < rows.length; i++) {
                    if (i % 2 == 0) {
                        rows[i].className = "evenrowcolor";
                    } else {
                        rows[i].className = "oddrowcolor";
                    }
                }
            }
        }
    </script>
    <style type="text/css">
        table.mytable {
            font-family: verdana, arial, sans-serif;
            font-size: 11px;
            color: #333333;
            border-width: 1px;
            border-color: #a9c6c9;
            border-collapse: collapse;
            width: 100%;
        }

        table.mytable th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #a9c6c9;
        }

        table.mytable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #a9c6c9;
        }

        .oddrowcolor {
            background-color: #d4e3e5;
        }

        .evenrowcolor {
            background-color: #c3dde0;
        }

        .mydiv {
            background: #c3dde0;
            line-height: 20px;
            border: 1px solid #0080FF;
            font-size: 12px;
            z-index: 999;
            width: 560px;
            height: 280px;
            left: 72%;
            top: 20%;
            margin-left: -150px !important;
            margin-top: -60px !important;
            margin-top: 0px;
            position: fixed !important;
            position: absolute;
        }
    </style>
    <title>后台工具汇总</title>
    <meta name="content-type" content="text/html;charset=UTF-8"/>
</head>
<body>
<br/>
<p align="center">红包运营工具汇总</p>
<p align="center">直接点击对应的工具，然后跳转到对应的工具</p>
<table id="simpleTools" class="mytable" align="center">
    <tr>
        <th width="30%">工具名</th>
        <th width="70%">说明</th>
    </tr>
    <tr>
        <td><a target="_blank"
               href="http://tpromo.tujia.com/tpromo/backdoor/getCouponFromCache.htm?wrapperId=waptujia001&customerActiveType=1&cityId=133&smart=false">查看缓存券</a>
        </td>
        <td>查看实际对外漏出的缓存券，smart参数表示是否查询智能红包券smart=true表示查询智能红包券</td>
    </tr>
    <tr>
        <td><a target="_blank"
               href="http://tpromo.tujia.com/tpromo/backdoor/getIntellectCouponFromCache.htm?wrapperId=waptujia001&customerActiveType=1&cityId=133">查看智能红包缓存券</a>
        </td>
        <td>查看智能红包券</td>
    </tr>
    <tr>
        <td><a target="_blank"
               href="http://tpromo.tujia.com/tpromo/client/test/updateAscendPathCache?activityChannelId=-1">刷新券缓存</a>
        </td>
        <td>重新刷新对外露出的券缓存</td>
    </tr>
    <tr>
        <td><a target="_blank"
               href="http://l-promo-async1.rd.tj.cna:8080/tpromo/backdoor/existsUnit.htm?activityCode=503304704743989248&unitId=998">查看定向房屋</a>
        </td>
        <td>查看某个房屋是否参与定向红包活动</td>
    </tr>

    <tr>
        <td><a target="_blank"
               href="http://l-promo-async1.rd.tj.cna:8080/tpromo/schedule/deleteByCode.htm?code=BnbCt_v40LZ">删除活动缓存</a>
        </td>
        <td>删除活动AscendPath缓存</td>
    </tr>

    <tr>
        <td><a target="_blank"
               href="http://tpromo.tujia.com/tpromo/backdoor/getReceivedCoupons.htm?activityCode=BnbCt_v40LZ">查看已领取的券个数</a>
        </td>
        <td>查看活动已领取的红包数,活动维度</td>
    </tr>

    <tr>
        <td><a target="_blank"
               href="http://l-hdsopenprice1.rd.tj.cna:8080/provider/thirdpart/queryCtrip.htm">查看原始携程报价</a></td>
        <td><input id="getParams" type="button" value="获取参数">
        </td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getUserReceivedCoupons.htm?memberId=30826604&activityCode=501047647730814976
">查看用户已领取的红包数</a></td>
        <td>查看用户已领取的红包数</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getPromoListByMemberId.htm?memberId=30826604
">查看用户已领取的所有缓存中的红包</a></td>
        <td>查看用户已领取的所有缓存中的红包</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/client/test/loadInventoryPools.htm?id=1,2
">查看InventoryPool缓存</a></td>
        <td>查看InventoryPool缓存</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/client/test/loadActivityChannels.htm?id=1,2
">查看ActivityChannel缓存</a></td>
        <td>查看ActivityChannel缓存</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/client/test/loadAscendPathByCodes.htm?codes=112212121221
">查看AscendPath缓存</a></td>
        <td>查看AscendPath缓存</td>
    </tr>
    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getLandlordCoupon.htm?hotelId=123
">查看房东红包缓存</a></td>
        <td>查看房东红包缓存</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getCouponComprehensiveInfo.htm?hotelId=123&activityCode=477509277129236480
">查看综合信息</a></td>
        <td>查看综合信息</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/clearBatchLandlord.htm?hotelId=123&activityCodes=0001
">清理房东红包缓存</a></td>
        <td>清理房东红包缓存</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getActivityLogs.htm?activityCode=0001
">查看活动日志</a></td>
        <td>查看活动日志,包括新增、上线、下线等操作日志</td>
    </tr>

</table>

<div id="params" class="mydiv" style="display:none;">
    <div align="right" style="padding:2px;z-index:2000;font-size:12px;cursor:pointer;position:absolute;right:0;"
         onclick="closeDivFun()">
        <span style="border:1px solid #000;width:12px;height:12px;line-height:12px;text-align:center;display:block;background-color:#FFFFFF;left:-20px;">×</span>
    </div>
    <br/>{"SearchCandidate": {
    "HotelID": 68343652,
    "DateRange": {
    "Start": "2021-10-04",
    "End": "2021-10-05"
    },
    "Occupancy": {
    "Adult": 1
    },
    "SearchTags": [{
    "Code": "MemberLevel",
    "Value": "10003"
    }],
    "UserProperties": [{
    "Code": "",
    "Category": ""
    }],
    "UserID": "M2483751368"
    },
    "Settings": {
    "PrimaryLangID": "zh-cn",
    "IsShowNonBookable": "F",
    "DisplayCurrency": "CNY"
    },
    "PagingSettings": {"PageSize":200}
    }
    <br/>
    <br/>
    <a align="center" href="javascript:closeDivFun()">关闭</a>
</div>
</body>
</html>
