<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.ownerExpense.home.createOrEditLabel"
          data-cy="OwnerExpenseCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.ownerExpense.home.createOrEditLabel')"
        >
          Create or edit a OwnerExpense
        </h2>
        <div>
          <div class="form-group" v-if="ownerExpense.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ownerExpense.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.ownerExpense.amount')" for="owner-expense-amount"
              >Amount</label
            >
            <input
              type="number"
              class="form-control"
              name="amount"
              id="owner-expense-amount"
              data-cy="amount"
              :class="{ valid: !$v.ownerExpense.amount.$invalid, invalid: $v.ownerExpense.amount.$invalid }"
              v-model.number="$v.ownerExpense.amount.$model"
              required
            />
            <div v-if="$v.ownerExpense.amount.$anyDirty && $v.ownerExpense.amount.$invalid">
              <small class="form-text text-danger" v-if="!$v.ownerExpense.amount.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.ownerExpense.amount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.ownerExpense.code')" for="owner-expense-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="owner-expense-code"
              data-cy="code"
              :class="{ valid: !$v.ownerExpense.code.$invalid, invalid: $v.ownerExpense.code.$invalid }"
              v-model="$v.ownerExpense.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.ownerExpense.expenseDate')" for="owner-expense-expenseDate"
              >Expense Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="owner-expense-expenseDate"
                  v-model="$v.ownerExpense.expenseDate.$model"
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
                id="owner-expense-expenseDate"
                data-cy="expenseDate"
                type="text"
                class="form-control"
                name="expenseDate"
                :class="{ valid: !$v.ownerExpense.expenseDate.$invalid, invalid: $v.ownerExpense.expenseDate.$invalid }"
                v-model="$v.ownerExpense.expenseDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.ownerExpense.notes')" for="owner-expense-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="owner-expense-notes"
              data-cy="notes"
              :class="{ valid: !$v.ownerExpense.notes.$invalid, invalid: $v.ownerExpense.notes.$invalid }"
              v-model="$v.ownerExpense.notes.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.ownerExpense.ownerExpenseType')"
              for="owner-expense-ownerExpenseType"
              >Owner Expense Type</label
            >
            <select
              class="form-control"
              id="owner-expense-ownerExpenseType"
              data-cy="ownerExpenseType"
              name="ownerExpenseType"
              v-model="ownerExpense.ownerExpenseType"
              required
            >
              <option v-if="!ownerExpense.ownerExpenseType" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  ownerExpense.ownerExpenseType && ownerExpenseTypeOption.id === ownerExpense.ownerExpenseType.id
                    ? ownerExpense.ownerExpenseType
                    : ownerExpenseTypeOption
                "
                v-for="ownerExpenseTypeOption in ownerExpenseTypes"
                :key="ownerExpenseTypeOption.id"
              >
                {{ ownerExpenseTypeOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.ownerExpense.ownerExpenseType.$anyDirty && $v.ownerExpense.ownerExpenseType.$invalid">
            <small
              class="form-text text-danger"
              v-if="!$v.ownerExpense.ownerExpenseType.required"
              v-text="$t('entity.validation.required')"
            >
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
            :disabled="$v.ownerExpense.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./owner-expense-update.component.ts"></script>
