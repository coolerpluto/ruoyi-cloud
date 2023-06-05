<template>
  <div>全员拥有商机数量Top10 滚动排名显示(默认未完的商机)，查询依据阶段进行查询</div>
</template>

<script>
export default {
  name: "SalespersonOppTop10",
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
