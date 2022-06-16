<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.ownerExpenseType.home.createOrEditLabel"
          data-cy="OwnerExpenseTypeCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.ownerExpenseType.home.createOrEditLabel')"
        >
          Create or edit a OwnerExpenseType
        </h2>
        <div>
          <div class="form-group" v-if="ownerExpenseType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ownerExpenseType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.ownerExpenseType.name')" for="owner-expense-type-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="owner-expense-type-name"
              data-cy="name"
              :class="{ valid: !$v.ownerExpenseType.name.$invalid, invalid: $v.ownerExpenseType.name.$invalid }"
              v-model="$v.ownerExpenseType.name.$model"
              required
            />
            <div v-if="$v.ownerExpenseType.name.$anyDirty && $v.ownerExpenseType.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.ownerExpenseType.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.ownerExpenseType.code')" for="owner-expense-type-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="owner-expense-type-code"
              data-cy="code"
              :class="{ valid: !$v.ownerExpenseType.code.$invalid, invalid: $v.ownerExpenseType.code.$invalid }"
              v-model="$v.ownerExpenseType.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.ownerExpenseType.notes')" for="owner-expense-type-notes"
              >Notes</label
            >
            <input
              type="text"
              class="form-control"
              name="notes"
              id="owner-expense-type-notes"
              data-cy="notes"
              :class="{ valid: !$v.ownerExpenseType.notes.$invalid, invalid: $v.ownerExpenseType.notes.$invalid }"
              v-model="$v.ownerExpenseType.notes.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.ownerExpenseType.createdDate')"
              for="owner-expense-type-createdDate"
              >Created Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="owner-expense-type-createdDate"
                  v-model="$v.ownerExpenseType.createdDate.$model"
                  name="createdDate"
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
                id="owner-expense-type-createdDate"
                data-cy="createdDate"
                type="text"
                class="form-control"
                name="createdDate"
                :class="{ valid: !$v.ownerExpenseType.createdDate.$invalid, invalid: $v.ownerExpenseType.createdDate.$invalid }"
                v-model="$v.ownerExpenseType.createdDate.$model"
              />
            </b-input-group>
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
            :disabled="$v.ownerExpenseType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./owner-expense-type-update.component.ts"></script>
