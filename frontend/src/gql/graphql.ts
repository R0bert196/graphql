/* eslint-disable */
import { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
  /** An RFC-3339 compliant Full Date Scalar */
  Date: { input: any; output: any; }
  /** A slightly refined version of RFC-3339 compliant DateTime Scalar */
  DateTime: { input: any; output: any; }
  /** String as Email with validation */
  Email: { input: any; output: any; }
  PhoneNumber: { input: any; output: any; }
};

export type AddCommentInput = {
  authorId?: InputMaybe<Scalars['ID']['input']>;
  postId?: InputMaybe<Scalars['ID']['input']>;
  text: Scalars['String']['input'];
};

export type AddPostInput = {
  authorId: Scalars['ID']['input'];
  description?: InputMaybe<Scalars['String']['input']>;
  title: Scalars['String']['input'];
};

export type AddUserInput = {
  name: Scalars['String']['input'];
  password: Scalars['String']['input'];
  roles: Scalars['String']['input'];
};

export type Comment = {
  __typename?: 'Comment';
  author?: Maybe<User>;
  id: Scalars['ID']['output'];
  post?: Maybe<Post>;
  text: Scalars['String']['output'];
};

export type Event = {
  __typename?: 'Event';
  eventType: Scalars['String']['output'];
  id: Scalars['ID']['output'];
};

export type Mutation = {
  __typename?: 'Mutation';
  addComment: Comment;
  addPost: Post;
  addUser: Scalars['ID']['output'];
  login: Scalars['String']['output'];
};


export type MutationAddCommentArgs = {
  addCommentInput?: InputMaybe<AddCommentInput>;
};


export type MutationAddPostArgs = {
  addPostInput: AddPostInput;
};


export type MutationAddUserArgs = {
  addUserInput: AddUserInput;
};


export type MutationLoginArgs = {
  password: Scalars['String']['input'];
  username: Scalars['String']['input'];
};

export type Post = {
  __typename?: 'Post';
  author?: Maybe<User>;
  comments: Array<Maybe<Comment>>;
  description?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  title: Scalars['String']['output'];
};

export type Query = {
  __typename?: 'Query';
  getComments: Array<Maybe<Comment>>;
  getEvent: Event;
  getPosts: Array<Post>;
  getRandomNumbers: Array<Scalars['Int']['output']>;
  getUsers: Array<User>;
  greet: Scalars['String']['output'];
  helloWorld?: Maybe<Scalars['String']['output']>;
  recentPosts: Array<Post>;
  test?: Maybe<Scalars['String']['output']>;
  validationCheck?: Maybe<Scalars['String']['output']>;
};


export type QueryGetCommentsArgs = {
  page: Scalars['Int']['input'];
  size: Scalars['Int']['input'];
};


export type QueryGetUsersArgs = {
  page: Scalars['Int']['input'];
  size: Scalars['Int']['input'];
};


export type QueryGreetArgs = {
  name: Scalars['String']['input'];
};


export type QueryRecentPostsArgs = {
  page: Scalars['Int']['input'];
  size: Scalars['Int']['input'];
};


export type QueryTestArgs = {
  bornAt?: InputMaybe<Scalars['DateTime']['input']>;
  date?: InputMaybe<Scalars['Date']['input']>;
  email?: InputMaybe<Scalars['Email']['input']>;
  phoneNumber?: InputMaybe<Scalars['PhoneNumber']['input']>;
};


export type QueryValidationCheckArgs = {
  email: Scalars['String']['input'];
  list: Array<Scalars['Int']['input']>;
  name: Scalars['String']['input'];
};

export type User = {
  __typename?: 'User';
  commentsForUser: Array<Maybe<Comment>>;
  id: Scalars['ID']['output'];
  name: Scalars['String']['output'];
  posts: Array<Post>;
  totalPosts: Scalars['Int']['output'];
};

export type HelloWorldQueryVariables = Exact<{ [key: string]: never; }>;


export type HelloWorldQuery = { __typename?: 'Query', helloWorld?: string | null };


export const HelloWorldDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"HelloWorld"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"helloWorld"}}]}}]} as unknown as DocumentNode<HelloWorldQuery, HelloWorldQueryVariables>;