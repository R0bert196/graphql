import { styled } from "@mui/material/styles";
import MuiAppBar, { AppBarProps as MuiAppBarProps } from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import MenuIcon from "@mui/icons-material/Menu";
import { FC } from "react";
import { Box } from "@mui/material";
import LoginIcon from "@mui/icons-material/Login";
import LogoutIcon from "@mui/icons-material/Logout";
import RegisterIcon from "@mui/icons-material/HowToReg";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../AuthProvider";
import PersonIcon from "@mui/icons-material/Person";

interface AppBarProps extends MuiAppBarProps {
  open?: boolean;
}

const UserOptionDiv = styled(Box)({
  width: "90%",
  justifyContent: "space-between",
  display: "flex",
});

const StyledLink = styled(Link)({
  color: "inherit",
  textDecoration: "none",
});

const drawerWidth = 240;

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== "open",
})<AppBarProps>(({ theme, open }) => ({
  transition: theme.transitions.create(["margin", "width"], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: `${drawerWidth}px`,
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));

interface Props {
  open: boolean;
  handleDrawerOpen: () => void;
}

const Header: FC<Props> = ({ open, handleDrawerOpen }) => {
  const AuthContext = useAuth();
  const navigate = useNavigate();

  console.log("AuthContext", AuthContext?.user);

  const handleLogout = () => {
    AuthContext?.clearToken();
    navigate("/login");
  };

  return (
    <AppBar position='fixed' open={open}>
      <Toolbar>
        <IconButton
          color='inherit'
          aria-label='open drawer'
          edge='start'
          onClick={handleDrawerOpen}
          sx={{ mr: 2, ...(open && { display: "none" }) }}
        >
          <MenuIcon />
        </IconButton>
        <UserOptionDiv>
          <Typography variant='h6' noWrap component='div'>
            Stack Overflow
          </Typography>
          <Box>
            {!AuthContext?.user ? (
              <>
                <StyledLink to='/login'>
                  <IconButton color='inherit' edge='start' sx={{ mr: 2 }}>
                    <LoginIcon />
                  </IconButton>
                </StyledLink>
                <StyledLink to='/register'>
                  <IconButton color='inherit' edge='start' sx={{ mr: 2 }}>
                    <RegisterIcon />
                  </IconButton>
                </StyledLink>
              </>
            ) : (
              <>
                <IconButton
                  onClick={handleLogout}
                  color='inherit'
                  edge='start'
                  sx={{ mr: 2 }}
                >
                  <LogoutIcon />
                </IconButton>
                <IconButton color='inherit' edge='start' sx={{ mr: 2 }}>
                  <PersonIcon /> {AuthContext.user.username}
                </IconButton>
              </>
            )}
          </Box>
        </UserOptionDiv>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
