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

        //�������õķ���
        function showDivFun() {
            document.getElementById('params').style.display = 'block';
        }//�ر��¼�
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
    <title>��̨���߻���</title>
    <meta name="content-type" content="text/html;charset=UTF-8"/>
</head>
<body>
<br/>
<p align="center">�����Ӫ���߻���</p>
<p align="center">ֱ�ӵ����Ӧ�Ĺ��ߣ�Ȼ����ת����Ӧ�Ĺ���</p>
<table id="simpleTools" class="mytable" align="center">
    <tr>
        <th width="30%">������</th>
        <th width="70%">˵��</th>
    </tr>
    <tr>
        <td><a target="_blank"
               href="http://tpromo.tujia.com/tpromo/backdoor/getCouponFromCache.htm?wrapperId=waptujia001&customerActiveType=1&cityId=133&smart=false">�鿴����ȯ</a>
        </td>
        <td>�鿴ʵ�ʶ���©���Ļ���ȯ��smart������ʾ�Ƿ��ѯ���ܺ��ȯsmart=true��ʾ��ѯ���ܺ��ȯ</td>
    </tr>
    <tr>
        <td><a target="_blank"
               href="http://tpromo.tujia.com/tpromo/backdoor/getIntellectCouponFromCache.htm?wrapperId=waptujia001&customerActiveType=1&cityId=133">�鿴���ܺ������ȯ</a>
        </td>
        <td>�鿴���ܺ��ȯ</td>
    </tr>
    <tr>
        <td><a target="_blank"
               href="http://tpromo.tujia.com/tpromo/client/test/updateAscendPathCache?activityChannelId=-1">ˢ��ȯ����</a>
        </td>
        <td>����ˢ�¶���¶����ȯ����</td>
    </tr>
    <tr>
        <td><a target="_blank"
               href="http://l-promo-async1.rd.tj.cna:8080/tpromo/backdoor/existsUnit.htm?activityCode=503304704743989248&unitId=998">�鿴������</a>
        </td>
        <td>�鿴ĳ�������Ƿ���붨�����</td>
    </tr>

    <tr>
        <td><a target="_blank"
               href="http://l-promo-async1.rd.tj.cna:8080/tpromo/schedule/deleteByCode.htm?code=BnbCt_v40LZ">ɾ�������</a>
        </td>
        <td>ɾ���AscendPath����</td>
    </tr>

    <tr>
        <td><a target="_blank"
               href="http://tpromo.tujia.com/tpromo/backdoor/getReceivedCoupons.htm?activityCode=BnbCt_v40LZ">�鿴����ȡ��ȯ����</a>
        </td>
        <td>�鿴�����ȡ�ĺ����,�ά��</td>
    </tr>

    <tr>
        <td><a target="_blank"
               href="http://l-hdsopenprice1.rd.tj.cna:8080/provider/thirdpart/queryCtrip.htm">�鿴ԭʼЯ�̱���</a></td>
        <td><input id="getParams" type="button" value="��ȡ����">
        </td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getUserReceivedCoupons.htm?memberId=30826604&activityCode=501047647730814976
">�鿴�û�����ȡ�ĺ����</a></td>
        <td>�鿴�û�����ȡ�ĺ����</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getPromoListByMemberId.htm?memberId=30826604
">�鿴�û�����ȡ�����л����еĺ��</a></td>
        <td>�鿴�û�����ȡ�����л����еĺ��</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/client/test/loadInventoryPools.htm?id=1,2
">�鿴InventoryPool����</a></td>
        <td>�鿴InventoryPool����</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/client/test/loadActivityChannels.htm?id=1,2
">�鿴ActivityChannel����</a></td>
        <td>�鿴ActivityChannel����</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/client/test/loadAscendPathByCodes.htm?codes=112212121221
">�鿴AscendPath����</a></td>
        <td>�鿴AscendPath����</td>
    </tr>
    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getLandlordCoupon.htm?hotelId=123
">�鿴�����������</a></td>
        <td>�鿴�����������</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getCouponComprehensiveInfo.htm?hotelId=123&activityCode=477509277129236480
">�鿴�ۺ���Ϣ</a></td>
        <td>�鿴�ۺ���Ϣ</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/clearBatchLandlord.htm?hotelId=123&activityCodes=0001
">�������������</a></td>
        <td>�������������</td>
    </tr>

    <tr>
        <td><a target="_blank" href="http://tpromo.tujia.com/tpromo/backdoor/getActivityLogs.htm?activityCode=0001
">�鿴���־</a></td>
        <td>�鿴���־,�������������ߡ����ߵȲ�����־</td>
    </tr>

</table>

<div id="params" class="mydiv" style="display:none;">
    <div align="right" style="padding:2px;z-index:2000;font-size:12px;cursor:pointer;position:absolute;right:0;"
         onclick="closeDivFun()">
        <span style="border:1px solid #000;width:12px;height:12px;line-height:12px;text-align:center;display:block;background-color:#FFFFFF;left:-20px;">��</span>
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
    <a align="center" href="javascript:closeDivFun()">�ر�</a>
</div>
</body>
</html>
