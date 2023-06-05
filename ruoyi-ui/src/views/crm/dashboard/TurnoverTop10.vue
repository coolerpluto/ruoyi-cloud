<template>
  <div>
    个人 商机成交总额 前十 10条 轮播滚动显示<br>
    参考:<br>
    https://blog.csdn.net/weixin_44109827/article/details/125481221<br>
    https://chenxuan0000.github.io/vue-seamless-scroll/guide/<br>
  </div>
</template>

<script>
export default {
  name: "TurnoverTop10",
  props: {
    dataScope: {
      type: String,
      default: 'all'
    },
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      list: [],
    };
  },
  created() {
    this.init();
  },
  mounted() {
  },
  methods: {
    init() {
      console.log(this.$options.name + ":" + this.dataScope);
    },
  },
}
</script>

<style scoped>

</style>
