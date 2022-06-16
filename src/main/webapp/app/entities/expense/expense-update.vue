<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.expense.home.createOrEditLabel"
          data-cy="ExpenseCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.expense.home.createOrEditLabel')"
        >
          Create or edit a Expense
        </h2>
        <div>
          <div class="form-group" v-if="expense.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="expense.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expense.amount')" for="expense-amount">Amount</label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="expense-amount"
              data-cy="amount"
              :class="{ valid: !$v.expense.amount.$invalid, invalid: $v.expense.amount.$invalid }"
              v-model.number="$v.expense.amount.$model"
              required
            />
            <div v-if="$v.expense.amount.$anyDirty && $v.expense.amount.$invalid">
              <small class="form-text text-danger" v-if="!$v.expense.amount.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.expense.amount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expense.expenseDate')" for="expense-expenseDate"
              >Expense Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="expense-expenseDate"
                  v-model="$v.expense.expenseDate.$model"
                  name="expenseDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="expense-expenseDate"
                data-cy="expenseDate"
                type="text"
                class="form-control"
                name="expenseDate"
                :class="{ valid: !$v.expense.expenseDate.$invalid, invalid: $v.expense.expenseDate.$invalid }"
                v-model="$v.expense.expenseDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expense.notes')" for="expense-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="expense-notes"
              data-cy="notes"
              :class="{ valid: !$v.expense.notes.$invalid, invalid: $v.expense.notes.$invalid }"
              v-model="$v.expense.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expense.code')" for="expense-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="expense-code"
              data-cy="code"
              :class="{ valid: !$v.expense.code.$invalid, invalid: $v.expense.code.$invalid }"
              v-model="$v.expense.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expense.expenseType')" for="expense-expenseType"
              >Expense Type</label
            >
            <select
              class="form-control"
              id="expense-expenseType"
              data-cy="expenseType"
              name="expenseType"
              v-model="expense.expenseType"
              required
            >
              <option v-if="!expense.expenseType" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  expense.expenseType && expenseTypeOption.id === expense.expenseType.id ? expense.expenseType : expenseTypeOption
                "
                v-for="expenseTypeOption in expenseTypes"
                :key="expenseTypeOption.id"
              >
                {{ expenseTypeOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.expense.expenseType.$anyDirty && $v.expense.expenseType.$invalid">
            <small class="form-text text-danger" v-if="!$v.expense.expenseType.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.expense.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./expense-update.component.ts"></script>
