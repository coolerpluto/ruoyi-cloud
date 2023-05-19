<template>
  <div class="app-container ">
    <!--    <div class="scan"></div>-->
    <div class="dashboard">
      <img class="imgaction" src="@/assets/images/scan.png">
    </div>
    <div style="color: white;">
      内容写这里
    </div>
  </div>
</template>

<script>

export default {
  name: "dashboard",
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
    };
  },
  created() {
    //this.getList();
    //this.getDeptTree();
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
    },
    /** 查询参数列表 */
    getList() {
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.dashboard {
  background-image: url("../../../assets/images/bg18.jpg");
  background-size: cover;
  //background-position: center;
  background-position-y: -30px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: -99;
}

.scan {
  background-image: url("../../../assets/images/scan.png");
  background-size: cover;
  background-position: center;
  animation-duration: 5s;
  animation-name: run;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  opacity: 1;
  width: 663px;
  height: 663px;
  position: absolute;
}

.imgaction {
  animation-duration: 5s;
  animation-name: run;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  opacity: 1;
  width: 663px;
  height: 663px;
  position: absolute;
}

@keyframes run {
  from {
    transform: rotateZ(0);
  }
  to {
    transform: rotateZ(360deg);
  }
}
</style>
