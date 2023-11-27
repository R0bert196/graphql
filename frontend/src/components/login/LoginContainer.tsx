import { Box, Button, TextField, Typography } from "@mui/material";
import { useFormik } from "formik";
import * as yup from "yup";
import { useLoginMutation } from "../../generated/graphql";

const validationSchema = yup.object({
  username: yup.string().required("Please enter your username"),
  password: yup.string().required("Please enter your password"),
});

const LoginContainer = () => {

    const [login, { data, loading, error }] = useLoginMutation({
      fetchPolicy: 'network-only'
  });

  const loginForm = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    validationSchema,
    onSubmit: (values) => {
        login({
            variables: {
            username: values.username,
            password: values.password
          }
      })
    },
  });

  return (
    <Box sx={{ textAlign: "center", marginTop: "2rem" }}>
      <Typography variant='h6'>Login</Typography>
      <Box sx={{ display: "flex", justifyContent: "center" }}>
        <form
          onSubmit={loginForm.handleSubmit}
          style={{
            display: "flex",
            flexDirection: "column",
            width: "30%",
            gap: "1rem",
          }}
        >
          <TextField
            id='username'
            name='username'
            placeholder='Enter username'
            label='Username'
            sx={{ marginTop: "10px" }}
            variant='outlined'
            value={loginForm.values.username}
            onChange={loginForm.handleChange}
            onBlur={loginForm.handleBlur}
            error={
              loginForm.touched.username && Boolean(loginForm.errors.username)
            }
            helperText={loginForm.errors.username}
          />
          <TextField
            type='password'
            id='password'
            name='password'
            placeholder='Enter password'
            label='Password'
            sx={{ marginTop: "10px" }}
            variant='outlined'
            value={loginForm.values.password}
            onChange={loginForm.handleChange}
            onBlur={loginForm.handleBlur}
            error={
              loginForm.touched.password && Boolean(loginForm.errors.password)
            }
            helperText={loginForm.errors.password}
          />
          <Button variant='contained' type='submit'>
            Login
          </Button>
        </form>
      </Box>
    </Box>
  );
};

export default LoginContainer;
