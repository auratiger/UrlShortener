import React, {useState, useEffect} from 'react';

import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';

import axios from 'axios';

const useStyles = makeStyles({
    root: {
        margin: "auto",
        width: '65%',
    },
    container: {
      maxHeight: "100%",
    },
});

const RedirectionList = (props) => {
    const classes = useStyles();
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(14);
    const [loaded, setLoaded] = useState();

    const columns = useState([
        { id: 'slug', label: 'Slug', minWidth: 170 },
        { id: 'url', label: 'Url', minWidth: 100 },
        {
          id: 'date',
          label: 'Created',
          minWidth: 50,
          align: 'right',
          format: (value) => value.to('en-US'),
        }
    ])[0];
    const [rows, setRows] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8081/urls")
            .then((response) => {
                console.log(response.data);
                let obj = [];
                response.data.map(u => {
                    obj.push({id: u.id, slug: u.slug, url: u.url, date: new Date(u.timestamp).toLocaleDateString("en-US")});
                });
                setRows(...rows, obj);
            }).catch((error) => {
                console.log(error);
            });
    }, []);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
      };
    
      const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
      };

    return(
        <Paper className={classes.root}>
            <TableContainer className={classes.container}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            {columns.map((column) => (
                            <TableCell
                                key={column.id}
                                align={column.align}
                                style={{ minWidth: column.minWidth }}
                            >
                                {column.label}
                            </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                    {rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                        return (
                        <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                            {columns.map((column) => {
                            const value = row[column.id];
                            return (
                                <TableCell key={column.id} align={column.align}>
                                {column.format && typeof value === 'number' ? column.format(value) : value}
                                </TableCell>
                            );
                            })}
                        </TableRow>
                        );
                    })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[20, 50, 100]}
                component="div"
                count={rows.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onChangePage={handleChangePage}
                onChangeRowsPerPage={handleChangeRowsPerPage}
            />
      </Paper>
    )
}

export default RedirectionList;