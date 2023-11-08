import Button, { ButtonProps } from "antd/es/button";

interface BigButtonProps {
  bold?: boolean;
}

function BigButton(props: ButtonProps & BigButtonProps) {
  const buttonProps = { ...props };
  delete buttonProps.bold;
  return (
    <Button
      {...buttonProps}
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
