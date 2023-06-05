<template>
  <div>个人或者全部人员 商机各阶段数量和分布: 数量使用曲线图 分布用饼状图</div>
</template>

<script>
export default {
  name: "OppStage",
  props: {
    dataScope: {
      type: String,
      default: ''
    },
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      list:[],
    };
  },
  created() {
    this.init();
  },
  mounted() {
  },
  methods: {
    init(){
      console.log(this.$options.name+":"+this.dataScope);
    },
  },
}
</script>

<style scoped>

</style>
