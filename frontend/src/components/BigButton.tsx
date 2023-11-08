import Button, { ButtonProps } from "antd/es/button";

interface BigButtonProps {
  bold?: boolean;
}

function BigButton(props: BigButtonProps & ButtonProps) {
  return (
    <Button
      {...props}
      size="large"
      shape="round"
      type="primary"
      style={{
        fontSize: "1.5em",
        paddingInline: "1em",
        height: "fit-content",
        fontWeight: props.bold ? "bold" : "normal",
       }}
    >
      {props.children}
    </Button>
  );
}

export default BigButton;
