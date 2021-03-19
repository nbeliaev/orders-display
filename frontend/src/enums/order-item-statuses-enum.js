'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

const NEW = 'new';
const IN_PROCESS = 'in-process';
const COMPLETED = 'completed';

exports.default = {
    NEW,
    IN_PROCESS,
    COMPLETED,

    getNextStatus(current) {
        if (current === NEW) {
            return IN_PROCESS
        } else if (current === IN_PROCESS) {
            return COMPLETED
        } else {
            return NEW
        }
    }
};