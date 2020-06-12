<script>
export default {
  functional: true,
  props: {
    render: Function,
    rowData: {
      type: null
    }
  },
  render: (h, ctx) => {
    const params = {
      rowData: ctx.props.rowData
    }
    return ctx.props.render(h, params)
  }
}
</script>
