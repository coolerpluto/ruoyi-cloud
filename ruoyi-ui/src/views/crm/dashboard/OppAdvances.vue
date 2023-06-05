<template>
  <div>商机最新进展: 个人展示10条 全部人员展示20条</div>
</template>

<script>
export default {
  name: "OppAdvances",
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
