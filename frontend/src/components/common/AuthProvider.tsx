import React, { FC, ReactNode, useEffect, useMemo, useState } from "react";
import { jwtDecode } from "jwt-decode";
import Loader from "./Loader";

interface Props {
  children: ReactNode;
}

interface User {
  username: string;
  roles: string[];
}

interface AuthContextData {
  saveToken: (token: string) => void;
  clearToken: () => void;
  user: User | null;
}

const AuthContext = React.createContext<AuthContextData | null>(null);

const AuthProvider: FC<Props> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const jwtToken = localStorage.getItem("token2");

    if (jwtToken && !user) {
      const decodedToken = jwtDecode<{ sub: string; roles: string[] }>(
        jwtToken
      );

      setUser({
        username: decodedToken.sub,
        roles: decodedToken.roles,
      });
    }
    setLoading(false);
  }, []);

  const clearToken = () => {
    localStorage.removeItem("token2");
    setUser(null);
  };

  const saveToken = (token: string) => {
    localStorage.setItem("token2", token);
    const decodedToken = jwtDecode<{ sub: string; roles: string[] }>(token);

    setUser({
      username: decodedToken.sub,
      roles: decodedToken.roles,
    });
  };

  const contextValue = useMemo(
    () => ({
      saveToken,
      clearToken,
      user,
    }),
    [user]
  );

  return (
    <AuthContext.Provider value={contextValue}>
      <>
        {loading && <Loader open={loading} />}
        {!loading && <> {children} </>}
      </>
    </AuthContext.Provider>
  );
};

export default AuthProvider;

export const useAuth = () => React.useContext(AuthContext);
