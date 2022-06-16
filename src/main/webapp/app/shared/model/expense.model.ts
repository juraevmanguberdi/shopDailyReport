import { IExpenseType } from '@/shared/model/expense-type.model';

export interface IExpense {
  id?: number;
  amount?: number;
  expenseDate?: Date | null;
  notes?: string | null;
  code?: string | null;
  expenseType?: IExpenseType;
}

export class Expense implements IExpense {
  constructor(
    public id?: number,
    public amount?: number,
    public expenseDate?: Date | null,
    public notes?: string | null,
    public code?: string | null,
    public expenseType?: IExpenseType
  ) {}
}
