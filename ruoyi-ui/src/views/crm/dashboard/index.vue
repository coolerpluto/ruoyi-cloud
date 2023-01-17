<template>
  <div class="app-container">
看板

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
