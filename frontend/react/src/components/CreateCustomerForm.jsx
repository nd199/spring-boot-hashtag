import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveCustomer} from "../services/Client.js";
import {errorAlert, successAlert} from "../services/AlertToast.js";


const MyTextInput = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={3}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};


const MySelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={3}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

// And now we can use these
const CreateCustomerForm = ({fetchCustomers}) => {
    return (
        <>

            <Formik

                initialValues={{
                    userName: '',
                    firstName: '',
                    lastName: '',
                    email: '',
                    password: '',
                    age: 0,
                    gender: '',
                }}

                validationSchema={Yup.object({
                    userName: Yup.string()
                        .min(6, 'Must be 6 characters or more')
                        .required('Required'),
                    firstName: Yup.string()
                        .min(3, 'Must be 3 characters or more')
                        .required('Required'),
                    lastName: Yup.string()
                        .min(3, 'Must be 3 characters or more')
                        .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required')
                        .lowercase(),
                    password: Yup.string()
                        .min(3, "minimum 3 characters")
                        .required("Required"),
                    age: Yup.number()
                        .min(16, "Must be at least 16")
                        .max(100, "100 Years")
                        .required('Required'),
                    gender: Yup.string()
                        .oneOf(
                            ['MALE', 'FEMALE'],
                            'Invalid gender'
                        )
                        .required('Required')
                })}


                onSubmit={(customer, {setSubmitting}) => {
                    setSubmitting(true);
                    saveCustomer(customer)
                        .then(response => {
                            console.log(response);
                            successAlert(
                                "Customer Created",
                                `Customer ${customer.firstName} profile,  
                                 created successfully`
                            )
                            fetchCustomers();
                        }).catch(error => {
                        console.log(error)
                        errorAlert(
                            error.code,
                            error.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false)
                    })
                }}
            >
                {({isValid, isSubmitting}) => (

                    <Form>
                        <Stack spacing={'20px'}>
                            <MyTextInput
                                label="User Name"
                                name="userName"
                                type="text"
                                placeholder="Jane.lopez"
                            />
                            <MyTextInput
                                label="First Name"
                                name="firstName"
                                type="text"
                                placeholder="Jane"
                            />
                            <MyTextInput
                                label="Last Name"
                                name="lastName"
                                type="text"
                                placeholder="Lopez"
                            />
                            <MyTextInput
                                label="Email Address"
                                name="email"
                                type="email"
                                placeholder="jane@codeNaren.com"
                            />
                            <MyTextInput
                                label="Password"
                                name="password"
                                type="password"
                                placeholder="Enter Your password"
                            />
                            <MyTextInput
                                label="Age"
                                name="age"
                                type="number"
                                placeholder="16-100"
                            />
                            <MySelect label="Gender" name="gender">
                                <option value="">Select Gender</option>
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                            </MySelect>
                            <Button disabled={!isValid || isSubmitting} type='submit'>Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateCustomerForm;