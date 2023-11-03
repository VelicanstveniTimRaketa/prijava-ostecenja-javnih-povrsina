interface PrijavaListItemField {
  title: string;
  value: string | number | React.ReactElement;
}

function PrijavaListItemField(props: PrijavaListItemField) {
  return (
    <div style={{ minWidth: "5em", maxWidth: "30em", margin: "0.5em" }}>
      <div style={{ fontWeight: "bold" }}>{props.title}</div>
      <div>{props.value}</div>
    </div>
  );
}

export default PrijavaListItemField;
