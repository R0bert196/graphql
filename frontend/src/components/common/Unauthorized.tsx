import { Alert } from "@mui/material";

const Unauthorized = () => {
  return (
    <Alert sx={{ marginTop: 2 }} severity='error'>
      You don't have the required permission to access this page!
    </Alert>
  );
};

export default Unauthorized;
