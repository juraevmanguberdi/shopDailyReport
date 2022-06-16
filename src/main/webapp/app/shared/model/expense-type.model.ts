export interface IExpenseType {
  id?: number;
  name?: string;
  code?: string | null;
  notes?: string | null;
  createdDate?: Date | null;
}

export class ExpenseType implements IExpenseType {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public notes?: string | null,
    public createdDate?: Date | null
  ) {}
}
