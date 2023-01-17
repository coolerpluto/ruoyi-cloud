<template>
  <div class="app-container">
  客户管理占个坑

  </div>
</template>

<script>

export default {
  name: "customer",
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
