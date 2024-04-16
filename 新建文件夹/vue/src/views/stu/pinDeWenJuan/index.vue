<template>
<div class="search">
    <Card>
        <Divider>情境感知与社会参与水平- 自评问卷</Divider>
        <Form label-position="top">
            <Row :gitter="16">
                <Col span="24">
                <FormItem label="1. 你如何理解国家或世界大事？">
                    <Select v-model="select1" style="width:570px">
                        <Option value="20"> A: 没有关注过</Option>
                        <Option value="15"> B: 偶尔通过新闻了解</Option>
                        <Option value="10"> C: 经常关注并思考其对国家的影响</Option>
                        <Option value="5"> D: 深入分析并讨论其对国家利益和形象的影响</Option>
                        <Option value="0"> E: 主动研究并采取措施维护国家利益和形象</Option>
                    </Select>
                </FormItem>
                </Col>
            </Row>
            <Row :gitter="16">
                <Col span="24">
                <FormItem label="2. 你参与集体活动的经历如何？">
                    <Select v-model="select2" style="width:570px">
                        <Option value="20"> A: 我很少参加集体活动</Option>
                        <Option value="15"> B: 我有时参加并做一些事情</Option>
                        <Option value="10"> C: 我积极参与并提供帮助</Option>
                        <Option value="5"> D: 我组织活动并带领他人</Option>
                        <Option value="0"> E: 我领导活动并取得显著成就</Option>
                    </Select>
                </FormItem>
                </Col>
            </Row>
            <Row :gitter="16">
                <Col span="24">
                <FormItem label="3. 你如何运用法律知识？">
                    <Select v-model="select3" style="width:570px">
                        <Option value="20"> A: 我对法律一无所知</Option>
                        <Option value="15"> B: 我了解一些基本法律常识</Option>
                        <Option value="10"> C: 我在必要时会查找法律信息</Option>
                        <Option value="5"> D: 我使用法律知识来维护自己的权利</Option>
                        <Option value="0"> E: 我积极参与法律知识的学习和宣传</Option>
                    </Select>
                </FormItem>
                </Col>
            </Row>
            <Row :gitter="16">
                <Col span="24">
                <FormItem label="4. 你如何遵守《中小学生守则》和《中学生日常行为规范》？">
                    <Select v-model="select4" style="width:570px">
                        <Option value="20"> A: 我经常忘记规则</Option>
                        <Option value="15"> B: 我有时会犯错</Option>
                        <Option value="10"> C: 我努力遵守规则</Option>
                        <Option value="5"> D: 我始终遵守规则</Option>
                        <Option value="0"> E: 我不仅遵守规则，还帮助他人理解规则的重要性</Option>
                    </Select>
                </FormItem>
                </Col>
            </Row>
            <Row :gitter="16">
                <Col span="24">
                <FormItem label="5. 你在团队合作中的表现如何？">
                  <Select v-model="select5" style="width:570px">
                    <Option value="20"> A: 我在团队中通常保持沉默 </Option>
                    <Option value="15"> B: 我有时会参与并提供帮助</Option>
                    <Option value="10"> C: 我在团队中积极交流并提出建议</Option>
                    <Option value="5"> D: 我在团队中担任领导或协调者角色</Option>
                    <Option value="0"> E: 我主导团队合作，确保任务顺利完成并提升团队士气</Option>
                  </Select>
                </FormItem>
                </Col>
            </Row>
            <Row :gitter="16">
                <Button type="success" @click="saveWenJuanFx">提交问卷</Button>
                <Button type="warning" @click="resetForm" style="marginLeft:30px">重置问卷</Button>
            </Row>
        </Form>
    </Card>
</div>
</template>

<script>
import {
    saveOneByType,
    getOneByType,
    editPinDeZiPing
} from "./api.js";
export default {
    name: "single-window",
    components: {},
    data() {
        return {
            select1: "0",
            select2: "0",
            select3: "0",
            select4: "0",
            select5: 0
        };
    },
    methods: {
        init() {
            this.resetForm();
            this.getOneByTypeFx();
        },
        saveWenJuanFx() {
            var that = this;
            if (isNaN(that.select5)) {
                that.select5 = 0;
            }
            var sum = 0;
            var strAns = "";
            sum += Number(that.select1) + Number(that.select2) + Number(that.select3) + Number(that.select4) + Number(that.select5);
            strAns += that.select1 + "," + that.select2 + "," + that.select3 + "," + that.select4 + "," + that.select5;
            saveOneByType({
                type: 2,
                value: strAns
            }).then(res => {
                if (res.success) {
                    this.$Message.success("问卷保存成功");
                }
            })
            editPinDeZiPing({
                grade: sum
            }).then(res => {
                if (res.success) {
                    this.$Message.success("更新总评价单成功");
                }
            })

        },
        resetForm() {
            this.select1 = "0";
            this.select2 = "0";
            this.select3 = "0";
            this.select4 = "0";
            this.select5 = 0;
        },
        getOneByTypeFx() {
            var that = this;
            getOneByType({
                type: 2
            }).then(res => {
                if (res.success) {
                    if (res.result.length > 0) {
                        that.select1 = res.result[0];
                        that.select2 = res.result[1];
                        that.select3 = res.result[2];
                        that.select4 = res.result[3];
                        that.select5 = res.result[4];
                    }
                }
            })
        }
    },
    mounted() {
        this.init();
    },
};
</script>

<style lang="less">
.search {
    .operation {
        margin-bottom: 2vh;
    }

    .select-count {
        font-weight: 600;
        color: #40a9ff;
    }

    .select-clear {
        margin-left: 10px;
    }

    .page {
        margin-top: 2vh;
    }

    .drop-down {
        margin-left: 5px;
    }
}

.filter-panel {
    width: 166px;
    min-height: 120px;
    height: 200px;
    position: absolute;
    background-color: white;
    z-index: 9999;
    margin-left: 1px;
    overflow-y: scroll;
    border: 1px solid blue;
    top: 35px;
    right: 10px;
}

.openSearch {
    position: absolute;
    right: 240px;
}

.openTip {
    position: absolute;
    right: 130px;
}

.showFilterPanelFlag {
    position: static !important;
    right: 10px;
    margin-right: 10px;
}

.ivu-table td {
    height: 38px !important;
}

.ivu-table-cell-with-expand {
    height: 38px !important;
    line-height: 38px !important;
}

.ivu-table .rowClassNmaeColor td {
    background-color: #b0b3b6 !important;
    color: #ffffff !important;
    font-size: 12px;
}
</style>
