import { useEffect } from "react";
import { useNavigate } from "react-router";

interface RoutesCheckedProps {
  if: boolean;
  elseNavigateTo: string | number;
  children: React.ReactNode | React.ReactNode[];
}

function Check(props: RoutesCheckedProps) {
  const navigate = useNavigate();

  useEffect(() => {
    if (!props.if) navigate(props.elseNavigateTo as string, { state: { checkFailed: true } }); // mrs
  }, [props.if, navigate, props.elseNavigateTo]);

  if (!props.if) return <div></div>;
  return props.children;
}

export default Check;
