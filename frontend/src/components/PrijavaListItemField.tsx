interface PrijavaListItemField {
  title: string;
  text: string;
}

function PrijavaListItemField(props: PrijavaListItemField) {
  return (
    <div style={{ width: "20em" }}>
      <div style={{ fontWeight: "bold" }}>{props.title}</div>
      <div>{props.text}</div>
    </div>
  );
}

export default PrijavaListItemField;
