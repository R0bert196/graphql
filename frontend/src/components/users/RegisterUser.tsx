import { Alert, Box, Button, TextField, Typography } from "@mui/material";
import { useFormik } from "formik";
import * as yup from "yup";
import Loader from "../common/Loader";
import { useAddUserMutation } from "../../generated/graphql";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import Chip from "@mui/material/Chip";
import { Theme, useTheme } from "@mui/material/styles";
import { useNavigate } from "react-router-dom";

const validationSchema = yup.object({
  username: yup.string().required("Please enter your username"),
  password: yup.string().required("Please enter your password"),
  roles: yup
    .array()
    .min(1, "Please select at least one role")
    .required("Please select your roles"),
});

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

function getStyles(name: string, personName: readonly string[], theme: Theme) {
  return {
    fontWeight:
      personName.indexOf(name) === -1
        ? theme.typography.fontWeightRegular
        : theme.typography.fontWeightMedium,
  };
}

const roles = ["ROLE_ADMIN", "ROLE_USER"];

const RegisterUser = () => {
  const naviagte = useNavigate();
  const theme = useTheme();
  const [addUser, { loading, error }] = useAddUserMutation();

  const registerForm = useFormik({
    initialValues: {
      username: "",
      password: "",
      roles: [],
    },
    validationSchema,
    onSubmit: (values) => {
      addUser({
        variables: {
          name: values.username,
          password: values.password,
          roles: values.roles.join(", "),
        },
      }).then(() => {
        naviagte("/login");
      });
    },
  });

  return (
    <Box sx={{ textAlign: "center", marginTop: "2rem" }}>
      <Typography variant='h6'>Login</Typography>
      <Box sx={{ display: "flex", justifyContent: "center" }}>
        <form
          onSubmit={registerForm.handleSubmit}
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
            value={registerForm.values.username}
            onChange={registerForm.handleChange}
            onBlur={registerForm.handleBlur}
            error={
              registerForm.touched.username &&
              Boolean(registerForm.errors.username)
            }
            helperText={registerForm.errors.username}
          />
          <TextField
            type='password'
            id='password'
            name='password'
            placeholder='Enter password'
            label='Password'
            sx={{ marginTop: "10px" }}
            variant='outlined'
            value={registerForm.values.password}
            onChange={registerForm.handleChange}
            onBlur={registerForm.handleBlur}
            error={
              registerForm.touched.password &&
              Boolean(registerForm.errors.password)
            }
            helperText={registerForm.errors.password}
          />
          <FormControl sx={{ width: "100%" }}>
            <InputLabel id='roles'>Roles</InputLabel>
            <Select
              labelId='roles'
              id='roles'
              name='roles'
              multiple
              value={registerForm.values.roles}
              onChange={registerForm.handleChange}
              input={<OutlinedInput id='select-multiple-chip' label='Chip' />}
              renderValue={(selected) => (
                <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                  {selected.map((value) => (
                    <Chip key={value} label={value} />
                  ))}
                </Box>
              )}
              MenuProps={MenuProps}
              error={
                registerForm.touched.roles && Boolean(registerForm.errors.roles)
              }
            >
              {roles.map((role) => (
                <MenuItem
                  key={role}
                  value={role}
                  style={getStyles(role, registerForm.values.roles, theme)}
                >
                  {role}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <Button variant='contained' type='submit'>
            Register
          </Button>
          <Loader open={loading} />
          {error && (
            <Alert severity='error'>{error.message}, please try again.</Alert>
          )}
        </form>
      </Box>
    </Box>
  );
};

export default RegisterUser;
