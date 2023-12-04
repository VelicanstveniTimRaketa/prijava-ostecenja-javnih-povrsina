import { useEffect } from "react";
import { useNavigate } from "react-router";

interface RoutesCheckedProps {
  if: boolean;
  elseNavigateTo: string;
  children: React.ReactNode | React.ReactNode[];
}

function Check(props: RoutesCheckedProps) {
  const navigate = useNavigate();

  useEffect(() => {
    if (!props.if) navigate(props.elseNavigateTo);
  }, [props.if, navigate, props.elseNavigateTo]);

  if (!props.if) return <div></div>;
  return props.children;
}

export default Check;
