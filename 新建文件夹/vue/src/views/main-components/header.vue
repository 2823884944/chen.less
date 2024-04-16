<template>
<div>
    <Row class="header">
        <img src="../../assets/tubiao1.png" width="220px">
        <div class="description">教评系统</div>
    </Row>
</div>
</template>

<script>
export default {
    name: "header"
};
</script>

<style lang="less">
.header {
    margin-top: 8vh;
    margin-bottom: 5vh;
    text-align: center;

    .description {
        font-size: 14px;
        color: rgba(0, 0, 0, 0.1);
        margin-top: 1vh;
    }
}
</style>
